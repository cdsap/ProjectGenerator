// server.js
const express = require('express');
const multer = require('multer');
const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');
const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));
const { v4: uuidv4 } = require('uuid');
const cors = require('cors');

const app = express();
const port = process.env.PORT || 8080;
const upload = multer({ dest: '/tmp/uploads' });

console.log('Starting server...');
console.log('Port:', port);
console.log('Environment:', process.env.NODE_ENV || 'development');

// ✅ Enable CORS for your GitHub Pages frontend only
app.use(cors({
    origin: 'https://cdsap.github.io',
    methods: ['GET', 'POST', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// Health check endpoint
app.get('/', (req, res) => {
    console.log('Root endpoint hit');
    res.status(200).json({ status: 'OK', message: 'Project Generator Backend is running' });
});

app.get('/health', (req, res) => {
    console.log('Health endpoint hit');
    res.status(200).json({ status: 'OK', timestamp: new Date().toISOString() });
});

app.post('/api/generate', upload.single('versions-file'), async (req, res) => {
    try {
        const body = req.body;
        const tmpDir = fs.mkdtempSync('/tmp/gen-');
        const outputName = `project-${uuidv4()}`;
        const outputZip = path.join(tmpDir, `${outputName}.zip`);

        // reCAPTCHA validation
        const isCaptchaValid = await verifyCaptcha(body.captchaToken);
        if (!isCaptchaValid) return res.status(403).send('Invalid CAPTCHA');

        // Limit modules to maximum of 1500
        const modules = Math.min(parseInt(body.modules) || 0, 300);
        console.log(`Modules requested: ${body.modules}, limited to: ${modules}`);

        // ✅ Build CLI args safely
        const args = [
            `--shape`, body.shape || 'rectangle',
            `--modules`, modules,
            `--layers`, body.layers || 5,
            `--language`, body.language || 'kts',
            `--type`, body.type || 'android',
            `--classes-module`, Math.min(parseInt(body['classes-module']) || 15, 15),
            `--classes-module-type`, body['classes-module-type'] || 'fixed',
            `--type-of-string-resources`, body['type-of-string-resources'] || 'normal',
            `--gradle`, body.gradle || 'GRADLE_9_2_0'
        ];

        // Add project name if provided
        if (body['project-name']) { args.push('--project-name', body['project-name']);}
        if (body['generate-unit-test'] === 'true' || body['generate-unit-test'] === true) args.push('--generate-unit-test');
        if (body['agp9'] === 'true' || body['agp9'] === true) args.push('--agp9');
        if (body.develocity === 'true' || body.develocity === true) args.push('--develocity');
        if (body['develocity-url']) args.push('--develocity-url', body['develocity-url']);
        if (body.type === 'android' && body.processor) args.push('--processor', body.processor);
        if (req.file) args.push('--versions-file', req.file.path);

        const command = `/usr/local/bin/projectGenerator generate-project ${args.join(' ')}`;
        console.log('Executing:', command);
        execSync(command, { cwd: tmpDir });

        execSync(`zip -r ${outputZip} .`, { cwd: tmpDir });
        res.download(outputZip, `${outputName}.zip`);
    } catch (err) {
        console.error(err);
        res.status(500).send('Generation failed.');
    }
});

async function verifyCaptcha(token) {
    const secret = process.env.RECAPTCHA_SECRET_KEY;
    console.log('Verifying CAPTCHA with token:', token.substring(0, 20) + '...');
    console.log('Using secret key:', secret.substring(0, 20) + '...');

    const response = await fetch(`https://www.google.com/recaptcha/api/siteverify`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `secret=${secret}&response=${token}`
    });
    const data = await response.json();
    console.log('CAPTCHA verification result:', data);
    return data.success === true;
}

const server = app.listen(port, '0.0.0.0', () => {
    console.log(`Server listening on port ${port}`);
    console.log(`Health check available at http://0.0.0.0:${port}/health`);
    console.log('Server is ready to accept connections');
}).on('error', (err) => {
    console.error('Failed to start server:', err);
    process.exit(1);
});

// Handle graceful shutdown
process.on('SIGTERM', () => {
    console.log('SIGTERM received, shutting down gracefully');
    server.close(() => {
        console.log('Server closed');
        process.exit(0);
    });
});

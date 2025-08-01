# Use Node.js base image
FROM node:22-slim

# Install Java and other dependencies
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    curl \
    zip \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Create app directory
WORKDIR /usr/src/app

# Copy files
COPY package*.json ./
RUN npm install
COPY . .

# Download CLI
RUN curl -L https://github.com/cdsap/ProjectGenerator/releases/download/v0.3.0/projectGenerator \
  --output /usr/local/bin/projectGenerator && \
  chmod 0755 /usr/local/bin/projectGenerator

# Expose expected Cloud Run port
ENV PORT=8080
EXPOSE 8080

# Add debugging
RUN ls -la && pwd

# Start the server with explicit node command
CMD ["/usr/local/bin/node", "server.cjs"]

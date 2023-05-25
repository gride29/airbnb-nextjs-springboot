# Use the official Node.js 16 Alpine image as the base
FROM node:16-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install the dependencies
RUN npm ci 

# Copy the rest of the application code
COPY . .

# Build the Next.js app
RUN npm run build

# Expose the desired port
EXPOSE 3000

# Set the command to start the app
CMD ["npm", "start"]

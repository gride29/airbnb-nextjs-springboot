/** @type {import('next').NextConfig} */
const nextConfig = {
	experimental: {
		appDir: true,
	},
	images: {
		domains: ["avatars.githubusercontent.com", "res.cloudinary.com"],
	},
	reactStrictMode: false,
	output: "standalone",
	env: {
		FRONTEND_URL: process.env.FRONTEND_URL,
		FRONTEND_URL_SHORT: process.env.FRONTEND_URL_SHORT,
		BACKEND_URL: process.env.BACKEND_URL,
	},
};

module.exports = nextConfig;

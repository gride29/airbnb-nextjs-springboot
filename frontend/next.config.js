/** @type {import('next').NextConfig} */
const nextConfig = {
	experimental: {
		appDir: true,
	},
	images: {
		domains: ["avatars.githubusercontent.com", "res.cloudinary.com"],
	},
};

module.exports = nextConfig;

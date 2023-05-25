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
};

module.exports = nextConfig;

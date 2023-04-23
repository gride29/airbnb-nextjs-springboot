import bcrypt from "bcrypt";
import axios from "axios";
import NextAuth, { AuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GithubProvider from "next-auth/providers/github";
import GoogleProvider from "next-auth/providers/google";

export const authOptions: AuthOptions = {
	providers: [
		GithubProvider({
			clientId: process.env.GITHUB_ID as string,
			clientSecret: process.env.GITHUB_SECRET as string,
		}),
		GoogleProvider({
			clientId: process.env.GOOGLE_CLIENT_ID as string,
			clientSecret: process.env.GOOGLE_CLIENT_SECRET as string,
		}),
		CredentialsProvider({
			name: "credentials",
			credentials: {
				email: { label: "email", type: "text" },
				password: { label: "password", type: "password" },
			},
			async authorize(credentials) {
				if (!credentials?.email || !credentials?.password) {
					throw new Error("Invalid credentials");
				}

				return axios
					.post("http://localhost:8080/api/auth/signin", {
						email: credentials.email,
						password: credentials.password,
					})
					.then((response) => {
						const user = response.data;
						return user;
					})
					.catch((error) => {
						console.log(error);
					});
			},
		}),
	],
	pages: {
		signIn: "/",
	},
	debug: process.env.NODE_ENV === "development",
	session: {
		strategy: "jwt",
	},
	secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);

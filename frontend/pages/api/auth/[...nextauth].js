import axios from "axios";
import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import { setCookie } from "nookies";

const nextAuthOptions = (req, res) => {
	return {
		providers: [
			CredentialsProvider({
				name: "credentials",
				credentials: {
					username: { label: "username", type: "text" },
					password: { label: "password", type: "password" },
				},
				async authorize(credentials) {
					if (!credentials?.username || !credentials?.password) {
						throw new Error("Invalid credentials");
					}

					return axios
						.post(`${process.env.BACKEND_URL}/api/auth/signin`, {
							username: credentials.username,
							password: credentials.password,
						})
						.then((response) => {
							const user = response.data;
							setCookie({ res }, "user", JSON.stringify(user), {
								maxAge: 7 * 24 * 60 * 60,
								path: "/",
								httpOnly: true,
							});
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
		events: {
			async signOut() {
				setCookie({ res }, "user", "", {
					maxAge: -1,
					path: "/",
					httpOnly: true,
				});
			},
		},
		secret: process.env.NEXTAUTH_SECRET,
	};
};

export default (req, res) => {
	return NextAuth(req, res, nextAuthOptions(req, res));
};

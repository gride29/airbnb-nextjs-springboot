import { cookies } from "next/headers";
import axios from "axios";

const parseJwt = (token: string) => {
	try {
		return JSON.parse(atob(token.split(".")[1]));
	} catch (e) {
		return null;
	}
};

export default async function getCurrentUser() {
	// Retrieve user from cookies
	const cookieStore = cookies();
	let user = JSON.parse(cookieStore.get("user")?.value || "null");

	if (user) {
		// Set custom headers for axios
		user.customHeaders = {
			Authorization: `Bearer ${user.accessToken}`,
		};
	}

	if (user) {
		const decodedJwt = parseJwt(user.accessToken);

		// If the token is expired, we sign out the user
		if (decodedJwt.exp * 1000 < Date.now()) {
			return undefined;
		}
	}

	if (user == null) {
		// If user is null, we check if the user is logged in with OAuth
		const cookieName = "JSESSIONID";
		const cookieValue = cookies().get(cookieName)?.value || null;

		if (cookieValue == null) {
			return undefined;
		}

		return axios
			.get("http://localhost:8080/api/auth/oauthUser", {
				headers: {
					Cookie: `${cookieName}=${cookieValue}`,
				},
			})
			.then((response) => {
				user = response.data;
				if (user) {
					user.JSESSIONID = cookieValue;
					// Set custom headers for axios
					user.customHeaders = {
						Cookie: `JSESSIONID=${user.JSESSIONID}`,
					};
					return user;
				}
			})
			.catch((error) => {
				console.log(error);
			});
	}

	return user;
}

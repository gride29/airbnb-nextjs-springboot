import { cookies } from "next/headers";
import axios from "axios";

export default async function getCurrentUser() {
	// Retrieve user from cookies
	const cookieStore = cookies();
	let user = JSON.parse(cookieStore.get("user")?.value || "null");

	if (user == null) {
		// If user is null, we check if the user is logged in with OAuth
		const cookieName = "JSESSIONID";
		const cookieValue = cookies().get(cookieName)?.value || null;

		console.log;

		return axios
			.get("http://localhost:8080/api/auth/oauthUser", {
				headers: {
					Cookie: `${cookieName}=${cookieValue}`,
				},
			})
			.then((response) => {
				user = response.data;
				return user;
			})
			.catch((error) => {
				console.log(error);
			});
	}
	return user;
}

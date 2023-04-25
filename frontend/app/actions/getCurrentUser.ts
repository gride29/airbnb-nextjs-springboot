import { cookies } from "next/headers";

export default function getCurrentUser() {
	// Retrieve user from cookies
	const cookieStore = cookies();
	const user = JSON.parse(cookieStore.get("user")?.value || "null");
	return user;
}

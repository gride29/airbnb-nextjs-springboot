import { NextResponse } from "next/server";
import axios from "axios";

export async function POST(request: Request) {
	const body = await request.json();
	const { email, username, password } = body;

	try {
		const response = await axios.post("http://localhost:8080/api/auth/signup", {
			username,
			email,
			password,
			roles: ["user"],
		});

		const user = response.data;

		return NextResponse.json(user);
	} catch (error) {
		console.error(error);
		return NextResponse.error();
	}
}

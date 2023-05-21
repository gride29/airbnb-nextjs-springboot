import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse
) {
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "POST": {
				try {
					if (
						req.headers.referer !== "http://localhost:3000/" &&
						req.headers.host !== "localhost:3000"
					) {
						console.log(req.headers.host);
						res.status(401).end("Not authorized");
						return;
					} else {
						const { email, username, password } = req.body;

						const response = await axios.post(
							"http://localhost:8080/api/auth/signup",
							{
								username,
								email,
								password,
								roles: ["user"],
							}
						);

						const user = response.data;

						res.status(200).json(user);
						return resolve();
					}
				} catch (error) {
					res.status(500).end();
					return resolve();
				}
			}
		}
		res.status(405).end();
		return resolve();
	});
}

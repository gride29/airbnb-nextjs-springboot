import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";

const cors = Cors({
	methods: ["GET", "POST", "DELETE"],
	origin: "http://localhost:3000",
});

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse
) {
	await cors(req, res, () => {});
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "POST": {
				try {
					if (
						req.headers.referer !== "http://127.0.0.1:3000/" &&
						req.headers.host !== "127.0.0.1:3000"
					) {
						console.log(req.headers.host);
						res.status(401).end("Not authorized");
						return;
					} else {
						const { email, username, password } = req.body;

						const response = await axios.post(
							`${process.env.BACKEND_URL}/api/auth/signup`,
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

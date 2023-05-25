import { setCookie } from "nookies";
import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";

const cors = Cors({
	methods: ["GET", "POST", "DELETE"],
	origin: "http://localhost:3000",
});

function oAuthSignOut(res: NextApiResponse) {
	setCookie({ res }, "JSESSIONID", "", {
		maxAge: -1,
		path: "/",
		httpOnly: true,
	});
}

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse
) {
	await cors(req, res, () => {});
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "GET": {
				try {
					// Only allow calls from the client side
					if (req.headers.referer !== "http://127.0.0.1:3000/") {
						res.status(401).end("Not authorized");
						return;
					} else {
						oAuthSignOut(res);
						res.status(200).send({ message: "OK" });
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

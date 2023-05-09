import { setCookie } from "nookies";
import { NextApiRequest, NextApiResponse } from "next";

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
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "GET": {
				try {
					// Only allow calls from the client side
					if (req.headers.referer !== "http://localhost:3000/") {
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

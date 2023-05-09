import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";

async function handleListingAdd(data: any, customHeaders: any) {
	let listingData = null;

	return axios
		.post("http://localhost:8080/api/listings", data, {
			headers: customHeaders,
		})
		.then((response) => {
			listingData = response.data;
			return listingData;
		})
		.catch((error) => {
			console.log(error);
		});
}

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse
) {
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "POST": {
				try {
					if (req.headers.referer !== "http://localhost:3000/") {
						res.status(401).end("Not authorized");
						return;
					} else {
						const { data, customHeaders } = req.body; // Assuming the request body contains a "data" field

						// console.log(customHeaders, data);

						await handleListingAdd(data, customHeaders);

						res.status(200).json({ message: "Data received" });
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

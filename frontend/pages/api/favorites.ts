import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";

const cors = Cors({
	methods: ["GET", "POST", "DELETE"],
	origin: "http://localhost:3000",
});

async function handleGetFavorites(userId: string, customHeaders: any) {
	let listings = null;

	return axios
		.get(`http://${process.env.BACKEND_URL}/api/favorites/${userId}`, {
			headers: customHeaders,
		})
		.then((response) => {
			listings = response.data;
			return listings;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleAddToFavorites(
	userId: string,
	listingId: string,
	customHeaders: any
) {
	let listings = null;

	return axios
		.post(
			`http://${process.env.BACKEND_URL}/api/favorites/${userId}`,
			{ favoriteListing: listingId },
			{
				headers: customHeaders,
			}
		)
		.then((response) => {
			listings = response.data;
			return listings;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleRemoveFromFavorites(
	userId: string,
	listingId: string,
	customHeaders: any
) {
	let listings = null;

	return axios
		.delete(`http://${process.env.BACKEND_URL}/api/favorites/${userId}`, {
			headers: customHeaders,
			data: {
				favoriteListing: listingId,
			},
		})
		.then((response) => {
			listings = response.data;
			return listings;
		})
		.catch((error) => {
			console.log(error);
		});
}

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
						const { customHeaders, userId, listingId, method } = req.body;

						let responseData = null;

						switch (method) {
							case "GET":
								responseData = await handleGetFavorites(userId, customHeaders);
								break;
							case "POST":
								responseData = await handleAddToFavorites(
									userId,
									listingId,
									customHeaders
								);
								break;
							case "DELETE":
								responseData = await handleRemoveFromFavorites(
									userId,
									listingId,
									customHeaders
								);
								break;
						}

						res.status(200).json(responseData);
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

import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";

const cors = Cors({
	methods: ["GET", "POST", "DELETE"],
	origin: "http://localhost:3000",
});

async function handleGetListings(customHeaders: any) {
	let listings = null;

	return axios
		.get(`http://${process.env.BACKEND_URL}/api/listings`, {
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

async function handleGetListingById(id: string, customHeaders: any) {
	let listings = null;

	return axios
		.get(`http://${process.env.BACKEND_URL}/api/listings/${id}`, {
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

async function handleGetListingsByUserId(userId: string, customHeaders: any) {
	let listings = null;

	return axios
		.get(`http://${process.env.BACKEND_URL}/api/listings/user/${userId}`, {
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

async function handleSearchListingsByQuery(query: any, customHeaders: any) {
	let listings = null;

	const queryParams = new URLSearchParams(query).toString();

	const url = `http://${process.env.BACKEND_URL}/api/listings/search?${queryParams}`;

	return axios
		.get(url, {
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

async function handleAddListing(data: any, customHeaders: any) {
	let addedListing = null;

	return axios
		.post(`http://${process.env.BACKEND_URL}/api/listings`, data, {
			headers: customHeaders,
		})
		.then((response) => {
			addedListing = response.data;
			return addedListing;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleRemoveListingByListingId(
	listingId: string,
	customHeaders: any
) {
	let listings = null;

	return axios
		.delete(`http://${process.env.BACKEND_URL}/api/listings/${listingId}`, {
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

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse
) {
	await cors(req, res, () => {});
	return new Promise<void>(async (resolve) => {
		switch (req.method) {
			case "POST": {
				try {
					res.setHeader("Access-Control-Allow-Origin", "*");
					res.setHeader(
						"Access-Control-Allow-Headers",
						"Origin, X-Requested-With, Content-Type, Accept"
					);
					if (
						req.headers.referer !== "http://127.0.0.1:3000/" &&
						req.headers.host !== "127.0.0.1:3000"
					) {
						console.log(req.headers.host);
						res.status(401).end("Not authorized");
						return;
					} else {
						const { listingData, customHeaders, id, userId, listingId, query } =
							req.body;

						let responseData = null;

						switch (true) {
							case "listingData" in req.body:
								responseData = await handleAddListing(
									listingData,
									customHeaders
								);
								break;
							case "id" in req.body:
								responseData = await handleGetListingById(id, customHeaders);
								break;
							case "userId" in req.body:
								responseData = await handleGetListingsByUserId(
									userId,
									customHeaders
								);
								break;
							case "listingId" in req.body:
								responseData = await handleRemoveListingByListingId(
									listingId,
									customHeaders
								);
								break;
							case "query" in req.body:
								responseData = await handleSearchListingsByQuery(
									query,
									customHeaders
								);
								break;
							case "customHeaders" in req.body:
								responseData = await handleGetListings(customHeaders);
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

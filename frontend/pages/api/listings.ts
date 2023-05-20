import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";

async function handleGetListings(customHeaders: any) {
	let listings = null;

	return axios
		.get("http://localhost:8080/api/listings", {
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
		.get(`http://localhost:8080/api/listings/${id}`, {
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
		.get(`http://localhost:8080/api/listings/user/${userId}`, {
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
		.post("http://localhost:8080/api/listings", data, {
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
		.delete(`http://localhost:8080/api/listings/${listingId}`, {
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
						const { listingData, customHeaders, id, userId, listingId } =
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

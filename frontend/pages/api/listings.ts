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
						const { data, customHeaders, id } = req.body; // Assuming the request body contains a "data" field

						let listingData = null;

						switch (true) {
							case "data" in req.body:
								listingData = await handleAddListing(data, customHeaders);
								break;
							case "id" in req.body:
								listingData = await handleGetListingById(id, customHeaders);
								break;
							case "customHeaders" in req.body:
								listingData = await handleGetListings(customHeaders);
								break;
						}

						res.status(200).json(listingData);
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

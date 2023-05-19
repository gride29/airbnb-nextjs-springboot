import axios from "axios";
import { NextApiRequest, NextApiResponse } from "next";

async function handleGetReservations(customHeaders: any) {
	let reservations = null;

	return axios
		.get("http://localhost:8080/api/reservations", {
			headers: customHeaders,
		})
		.then((response) => {
			reservations = response.data;
			return reservations;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleGetReservationsByListingId(
	listingId: string,
	customHeaders: any
) {
	let reservation = null;

	return axios
		.get(`http://localhost:8080/api/reservations/listing/${listingId}`, {
			headers: customHeaders,
		})
		.then((response) => {
			reservation = response.data;
			return reservation;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleGetReservationsByUserId(
	userId: string,
	customHeaders: any
) {
	let reservation = null;

	return axios
		.get(`http://localhost:8080/api/reservations/user/${userId}`, {
			headers: customHeaders,
		})
		.then((response) => {
			reservation = response.data;
			return reservation;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleGetReservationsByOwnerId(
	ownerId: string,
	customHeaders: any
) {
	let reservation = null;

	return axios
		.get(`http://localhost:8080/api/reservations/owner/${ownerId}`, {
			headers: customHeaders,
		})
		.then((response) => {
			reservation = response.data;
			return reservation;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleAddReservation(data: any, customHeaders: any) {
	let addedReservation = null;

	return axios
		.post("http://localhost:8080/api/reservations", data, {
			headers: customHeaders,
		})
		.then((response) => {
			addedReservation = response.data;
			return addedReservation;
		})
		.catch((error) => {
			console.log(error);
		});
}

async function handleRemoveReservationById(
	reservationId: string,
	customHeaders: any
) {
	let status = null;

	return axios
		.delete(`http://localhost:8080/api/reservations/${reservationId}`, {
			headers: customHeaders,
		})
		.then((response) => {
			status = response.status;
			return status;
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
						const {
							reservationData,
							customHeaders,
							listingId,
							userId,
							ownerId,
							reservationId,
						} = req.body;

						let responseData = null;

						switch (true) {
							case "reservationData" in req.body:
								responseData = await handleAddReservation(
									reservationData,
									customHeaders
								);
								break;
							case "listingId" in req.body:
								responseData = await handleGetReservationsByListingId(
									listingId,
									customHeaders
								);
								break;
							case "userId" in req.body:
								responseData = await handleGetReservationsByUserId(
									userId,
									customHeaders
								);
								break;
							case "ownerId" in req.body:
								responseData = await handleGetReservationsByOwnerId(
									ownerId,
									customHeaders
								);
								break;
							case "reservationId" in req.body:
								responseData = await handleRemoveReservationById(
									reservationId,
									customHeaders
								);
							case "customHeaders" in req.body:
								responseData = await handleGetReservations(customHeaders);
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

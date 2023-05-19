"use client";

import React, { useCallback, useState } from "react";
import Container from "../components/Container";
import Heading from "../components/Heading";
import { useRouter } from "next/navigation";
import axios from "axios";
import toast from "react-hot-toast";
import ListingCard from "../components/listings/ListingCard";

interface ReservationsClientProps {
	reservations?: any;
	currentUser?: any;
}

const ReservationsClient: React.FC<ReservationsClientProps> = ({
	reservations = [],
	currentUser,
}) => {
	const router = useRouter();
	const [deletingId, setDeletingId] = useState("");

	const onCancel = useCallback((reservationId: string) => {
		setDeletingId(reservationId);

		const customData = {
			reservationId: reservationId,
			customHeaders: currentUser.customHeaders,
		};

		axios
			.post("/api/reservations", customData)
			.then(() => {
				toast.success("Reservation cancelled");
				router.refresh();
			})
			.catch((error) => {
				console.log(error);
				toast.error("Failed to cancel reservation");
			})
			.finally(() => {
				setDeletingId("");
			});
	}, []);
	return (
		<Container>
			<Heading title="Reservations" subtitle="Bookings on your properties" />
			<div className="mt-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-8">
				{reservations.map((reservation: any) => (
					<ListingCard
						key={reservation.id}
						data={reservation.listing}
						reservation={reservation}
						actionId={reservation.id}
						onAction={onCancel}
						disabled={deletingId === reservation.id}
						actionLabel="Cancel guest reservation"
						currentUser={currentUser}
					/>
				))}
			</div>
		</Container>
	);
};

export default ReservationsClient;

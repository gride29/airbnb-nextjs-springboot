"use client";

import React, { useCallback, useState } from "react";
import Container from "../components/Container";
import Heading from "../components/Heading";
import { useRouter } from "next/navigation";
import axios from "axios";
import toast from "react-hot-toast";
import ListingCard from "../components/listings/ListingCard";

interface PropertiesClientProps {
	listings: any;
	currentUser?: any;
}

const PropertiesClient: React.FC<PropertiesClientProps> = ({
	listings,
	currentUser,
}) => {
	const router = useRouter();
	const [deletingId, setDeletingId] = useState("");

	const onCancel = useCallback((listingId: string) => {
		setDeletingId(listingId);

		const customData = {
			listingId,
			customHeaders: currentUser.customHeaders,
		};

		console.log(customData);

		axios
			.post("/api/listings", customData)
			.then(() => {
				toast.success("Properties removed");
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
			<Heading title="Properties" subtitle="List of your properties" />
			<div className="mt-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-8">
				{listings
					.sort((a: any, b: any) =>
						new Date(b.createdAt) > new Date(a.createdAt) ? 1 : -1
					)
					.map((listing: any) => (
						<ListingCard
							key={listing.id}
							data={listing}
							actionId={listing.id}
							onAction={onCancel}
							disabled={deletingId === listing.id}
							actionLabel="Delete property"
							currentUser={currentUser}
						/>
					))}
			</div>
		</Container>
	);
};

export default PropertiesClient;

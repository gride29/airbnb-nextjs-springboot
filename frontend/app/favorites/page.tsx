import React from "react";
import ClientOnly from "../components/ClientOnly";
import EmptyState from "../components/EmptyState";
import getCurrentUser from "../actions/getCurrentUser";
import { getFavorites } from "../actions/favoritesActions";
import FavoritesClient from "./FavoritesClient";

const ListingPage = async () => {
	const currentUser = await getCurrentUser();
	const userFavoriteListings = await getFavorites(currentUser);
	const favoriteListings = userFavoriteListings.favoriteListings;

	if (favoriteListings.length === 0) {
		return (
			<ClientOnly>
				<EmptyState
					title="No favorites found"
					subtitle="Looks like you have no favorite listings"
				/>
			</ClientOnly>
		);
	}

	return (
		<ClientOnly>
			<FavoritesClient
				favoriteListings={favoriteListings}
				currentUser={currentUser}
			/>
		</ClientOnly>
	);
};

export default ListingPage;

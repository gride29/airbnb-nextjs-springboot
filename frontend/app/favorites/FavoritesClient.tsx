import React from "react";
import Container from "../components/Container";
import Heading from "../components/Heading";
import ListingCard from "../components/listings/ListingCard";

interface FavoritesClientProps {
	favoriteListings?: any;
	currentUser: any;
}

const FavoritesClient: React.FC<FavoritesClientProps> = ({
	favoriteListings,
	currentUser,
}) => {
	return (
		<Container>
			<Heading title="Favorites" subtitle="List of places you have favorited" />
			<div className="mt-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-8">
				{favoriteListings.map((listing: any) => {
					return (
						<ListingCard
							currentUser={currentUser}
							key={listing.id}
							data={listing}
							heartDisabled={true}
						/>
					);
				})}
			</div>
		</Container>
	);
};

export default FavoritesClient;

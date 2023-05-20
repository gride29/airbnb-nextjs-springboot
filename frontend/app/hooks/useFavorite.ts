import axios from "axios";
import { useRouter } from "next/navigation";
import { useCallback, useMemo } from "react";
import { toast } from "react-hot-toast";

import useLoginModal from "./useLoginModal";
import {
	addToFavorites,
	getFavorites,
	removeFromFavorites,
} from "../actions/favoritesActions";

interface IUseFavorite {
	listingId: string;
	currentUser?: any | null;
}

const useFavorite = ({ listingId, currentUser }: IUseFavorite) => {
	const router = useRouter();
	const loginModal = useLoginModal();

	const hasFavorited = useMemo(async () => {
		const list = (await getFavorites(currentUser)).favoriteListings || [];

		return list.some((item: { id: string }) => item.id === listingId);
	}, [currentUser, listingId]);

	const toggleFavorite = useCallback(
		async (e: React.MouseEvent<HTMLDivElement>) => {
			e.stopPropagation();

			if (!currentUser) {
				return loginModal.onOpen();
			}

			try {
				if (await hasFavorited) {
					removeFromFavorites(listingId, currentUser);
				} else {
					addToFavorites(listingId, currentUser);
				}

				router.refresh();
				toast.success("Successfully updated favorites");
			} catch (error) {
				toast.error("Something went wrong");
			}
		},
		[currentUser, hasFavorited, listingId, loginModal, router]
	);

	return {
		hasFavorited,
		toggleFavorite,
	};
};

export default useFavorite;

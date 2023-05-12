import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import useFavorite from "../hooks/useFavorite";
import { useEffect, useState } from "react";

interface HeartButtonProps {
	listingId: string;
	currentUser?: any | null;
}

const HeartButton: React.FC<HeartButtonProps> = ({
	listingId,
	currentUser,
}) => {
	const { hasFavorited, toggleFavorite } = useFavorite({
		listingId,
		currentUser,
	});

	const [favorited, setFavorited] = useState<boolean>(false);

	useEffect(() => {
		const fetchData = async () => {
			const isFavorited = await hasFavorited;
			setFavorited(isFavorited);
		};

		fetchData();
	}, [hasFavorited]);

	return (
		<div
			onClick={toggleFavorite}
			className="relative hover:opacity-80 transition cursor-pointer"
		>
			<AiOutlineHeart
				size={28}
				className="fill-white absolute -top-[2px] -right-[2px]"
			/>
			<AiFillHeart
				size={24}
				className={favorited ? "fill-rose-500" : "fill-neutral-500/70"}
			/>
		</div>
	);
};

export default HeartButton;

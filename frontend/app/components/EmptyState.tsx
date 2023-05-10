"use client";

import { useRouter } from "next/navigation";
import Heading from "./Heading";
import Button from "./Button";

interface EmptyStateProps {
	title?: string;
	subtitle?: string;
	showReset?: boolean;
}

const EmptyState: React.FC<EmptyStateProps> = ({
	title = "No results",
	subtitle = "Try adjusting your search or filters to find what you're looking for.",
	showReset = false,
}) => {
	const router = useRouter();

	return (
		<div className="h-[60vh] flex flex-col gap-2 justify-center items-center">
			<Heading title={title} subtitle={subtitle} />
			<div className="w-48 mt-6">
				{showReset && (
					<Button
						outline
						label="Reset filters"
						onClick={() => router.push("/")}
					/>
				)}
			</div>
		</div>
	);
};

export default EmptyState;

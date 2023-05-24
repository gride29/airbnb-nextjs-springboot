"use client";

import { useEffect } from "react";
import ErrorState from "./components/ErrorState";

interface ErrorProps {
	error: Error;
}

const Error: React.FC<ErrorProps> = ({ error }) => {
	useEffect(() => {
		console.error(error);
	}, [error]);
	return <ErrorState title="Error" subtitle="Something went wrong" />;
};

export default Error;

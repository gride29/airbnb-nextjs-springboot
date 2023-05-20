"use client";

import Image from "next/image";
import { useRouter } from "next/navigation";

const Logo = () => {
	const router = useRouter();

	return (
		<Image
			onClick={() => router.push("/")}
			alt="Logo"
			className="hidden md:block cursor-pointer w-auto mr-3"
			height={100}
			width={100}
			src="/images/logo.png"
			priority
		/>
	);
};

export default Logo;

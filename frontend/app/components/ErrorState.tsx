"use client";

interface ErrorStateProps {
	title?: string;
	subtitle?: string;
}

const ErrorState: React.FC<ErrorStateProps> = ({ title, subtitle }) => {
	return (
		<div className="h-[60vh] flex flex-col gap-2 justify-center items-center">
			<div className="text-center">
				<div className="text-2xl text-rose-500 font-bold">{title}</div>
				<div className="font-light text-neutral-500 mt-2">{subtitle}</div>
			</div>
		</div>
	);
};

export default ErrorState;

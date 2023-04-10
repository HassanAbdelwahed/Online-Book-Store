import "./BookTable.css";
import { useState } from "react";
const BookTable = ({ books }) => {
	const [page, setPage] = useState(1);
	let content = [];
	let size = 20;
	let start = (page - 1) * size;
	let end = page * size;

	for (let i = start; i < end && i < books.length; i++) {
		content.push(
			<a className='tr' href='/' key={books[i].ISBN}>
				<div className='td'>{books[i].ISBN}</div>
				<div className='td'>{books[i].title}</div>
				<div className='td'>{books[i].year}</div>
				<div className='td'>{books[i].category}</div>
				<div className='td'>{books[i].price}</div>
				<div className='td'>{books[i].amount}</div>
				<div className='td'>{books[i].publisher}</div>
			</a>,
		);
	}
	function changePage() {
		content = [];
		let start = (page - 1) * size;
		let end = page * size;
		for (let i = start; i < end; i++) {
			content.push(
				<a className='tr' href='/' key={books[i].ISBN}>
					<div className='td'>{books[i].ISBN}</div>
					<div className='td'>{books[i].title}</div>
					<div className='td'>{books[i].year}</div>
					<div className='td'>{books[i].category}</div>
					<div className='td'>{books[i].price}</div>
					<div className='td'>{books[i].amount}</div>
					<div className='td'>{books[i].publisher}</div>
				</a>,
			);
		}
	}

	let notFound = books == null ? true : false;

	return (
		<>
			{!notFound && (
				<div className='table'>
					<div className='th'>ISBN</div>
					<div className='th'>Title</div>
					<div className='th'>Year</div>
					<div className='th'>Category</div>
					<div className='th'>Price</div>
					<div className='th'>Amount</div>
					<div className='th'>Publisher</div>
					{content.map((item) => item)}
				</div>
			)}
			<div className='changing-pages'>
				{page !== 1 && (
					<button
						onClick={() => {
							setPage(page - 1);
							changePage();
						}}>
						Previous
					</button>
				)}
				<p>{page}</p>
				{page * size < books.length && (
					<button
						onClick={() => {
							setPage(page + 1);
							changePage();
						}}>
						Next
					</button>
				)}
			</div>
			{notFound && <p className='no-results'>No results</p>}
		</>
	);
};

export default BookTable;

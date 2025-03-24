-- Add authors
INSERT INTO author (name, biography) SELECT 'Elena Caldwell', 'A former archaeologist, Elena writes historical fiction inspired by lost civilizations and forgotten legends.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'Elena Caldwell');
INSERT INTO author (name, biography) SELECT 'Marcus D. Holloway', 'A cybersecurity expert by day, Marcus crafts thrilling techno-thrillers that explore the dark side of the digital age.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'Marcus D. Holloway');
INSERT INTO author (name, biography) SELECT 'Isabella Finch', 'A poet and nature enthusiast, Isabella captures the beauty of the wilderness in her lyrical prose and heartfelt poetry collections.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'Isabella Finch');
INSERT INTO author (name, biography) SELECT 'Jasper Monroe', 'A retired detective, Jasper channels his years of experience into writing gripping crime novels filled with intricate mysteries.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'Jasper Monroe');
INSERT INTO author (name, biography) SELECT 'Lillian Voss', 'A speculative fiction writer, Lillian blends mythology and futuristic technology to create immersive sci-fi and fantasy worlds.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'Lillian Voss');
INSERT INTO author (name, biography) SELECT 'James Brown', 'A famed author based in London, James is an expert on suspenseful crime stories and mystifying story-telling.' WHERE NOT EXISTS(SELECT 1 FROM author WHERE name = 'James Brown');

-- Add books
INSERT INTO book (title, publication_year, isbn) SELECT 'Echoes of the Forgotten', '2023', '978-1-5289-3095-4' WHERE NOT EXISTS(SELECT 1 FROM book WHERE isbn = '978-1-5289-3095-4');
INSERT INTO book (title, publication_year, isbn) SELECT 'The Digital Maze', '2022', '978-0-1443-6017-9' WHERE NOT EXISTS(SELECT 1 FROM book WHERE isbn = '978-0-1443-6017-9');
INSERT INTO book (title, publication_year, isbn) SELECT 'Whispers in the Wilderness', '1992', '978-3-7752-8982-1' WHERE NOT EXISTS(SELECT 1 FROM book WHERE isbn = '978-3-7752-8982-1');
INSERT INTO book (title, publication_year, isbn) SELECT 'Shadows of the Silent City', '2001', '978-1-2145-7843-2' WHERE NOT EXISTS(SELECT 1 FROM book WHERE isbn = '978-1-2145-7843-2');
INSERT INTO book (title, publication_year, isbn) SELECT 'The Last Starship''s Journey', '1984', '978-0-5576-2110-8' WHERE NOT EXISTS(SELECT 1 FROM book WHERE isbn = '978-0-5576-2110-8');

-- Add author-book relationships
INSERT INTO author_books (author_id, book_id) SELECT 1, 5 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 1 AND book_id = 5);
INSERT INTO author_books (author_id, book_id) SELECT 6, 5 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 6 AND book_id = 5);
INSERT INTO author_books (author_id, book_id) SELECT 2, 4 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 2 AND book_id = 4);
INSERT INTO author_books (author_id, book_id) SELECT 3, 3 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 3 AND book_id = 3);
INSERT INTO author_books (author_id, book_id) SELECT 4, 2 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 4 AND book_id = 2);
INSERT INTO author_books (author_id, book_id) SELECT 5, 1 WHERE NOT EXISTS(SELECT 1 FROM author_books WHERE author_id = 5 AND book_id = 1);

-- Add membership cards
INSERT INTO membership_card (card_number, issue_date, expiry_date) SELECT '4821-7385-1923-6471', '2024-03-15', '2026-03-15' WHERE NOT EXISTS(SELECT 1 FROM membership_card WHERE card_number = '4821-7385-1923-6471');
INSERT INTO membership_card (card_number, issue_date, expiry_date) SELECT '1937-8542-6819-2374', '2025-01-10', '2027-01-10' WHERE NOT EXISTS(SELECT 1 FROM membership_card WHERE card_number = '1937-8542-6819-2374');
INSERT INTO membership_card (card_number, issue_date, expiry_date) SELECT '5741-2389-4021-9536', '2024-02-22', '2026-02-22' WHERE NOT EXISTS(SELECT 1 FROM membership_card WHERE card_number = '5741-2389-4021-9536');
INSERT INTO membership_card (card_number, issue_date, expiry_date) SELECT '8369-1745-9201-4682', '2023-07-05', '2025-07-05' WHERE NOT EXISTS(SELECT 1 FROM membership_card WHERE card_number = '8369-1745-9201-4682');
INSERT INTO membership_card (card_number, issue_date, expiry_date) SELECT '6502-3847-2105-7689', '2024-10-18', '2026-10-18' WHERE NOT EXISTS(SELECT 1 FROM membership_card WHERE card_number = '6502-3847-2105-7689');

-- Add library members
INSERT INTO library_member (name, email, membership_date, membership_card_id) SELECT 'Olivia Bennett', 'olivia.bennett@example.com', '2024-03-05', 1 WHERE NOT EXISTS(SELECT 1 FROM library_member WHERE email = 'olivia.bennett@example.com');
INSERT INTO library_member (name, email, membership_date, membership_card_id) SELECT 'Ethan Roberts', 'ethan.roberts@example.com', '2025-01-05', 2 WHERE NOT EXISTS(SELECT 1 FROM library_member WHERE email = 'ethan.roberts@example.com');
INSERT INTO library_member (name, email, membership_date, membership_card_id) SELECT 'Mia Harrison', 'mia.harrison@example.com', '2024-02-18', 3 WHERE NOT EXISTS(SELECT 1 FROM library_member WHERE email = 'mia.harrison@example.com');
INSERT INTO library_member (name, email, membership_date, membership_card_id) SELECT 'Lucas Mitchell', 'lucas.mitchell@example.com', '2023-07-01', 4 WHERE NOT EXISTS(SELECT 1 FROM library_member WHERE email = 'lucas.mitchell@example.com');
INSERT INTO library_member (name, email, membership_date, membership_card_id) SELECT 'Sophia Clark', 'sophia.clark@example.com', '2024-10-10', 5 WHERE NOT EXISTS(SELECT 1 FROM library_member WHERE email = 'sophia.clark@example.com');

-- Add borrow records
INSERT INTO borrow_record (borrow_date, return_date, book_id, library_member_id) SELECT '2025-02-20', '2025-03-01', 2, 4 WHERE NOT EXISTS(SELECT 1 FROM borrow_record WHERE book_id = 2 AND library_member_id = 4);
INSERT INTO borrow_record (borrow_date, return_date, book_id, library_member_id) SELECT '2025-03-05', '2025-03-12', 5, 3 WHERE NOT EXISTS(SELECT 1 FROM borrow_record WHERE book_id = 5 AND library_member_id = 3);
INSERT INTO borrow_record (borrow_date, return_date, book_id, library_member_id) SELECT '2025-03-10', '2025-03-20', 1, 2 WHERE NOT EXISTS(SELECT 1 FROM borrow_record WHERE book_id = 1 AND library_member_id = 2);
INSERT INTO borrow_record (borrow_date, return_date, book_id, library_member_id) SELECT '2025-03-15', null, 3, 5 WHERE NOT EXISTS(SELECT 1 FROM borrow_record WHERE book_id = 3 AND library_member_id = 5);
INSERT INTO borrow_record (borrow_date, return_date, book_id, library_member_id) SELECT '2025-03-23', null, 4, 1 WHERE NOT EXISTS(SELECT 1 FROM borrow_record WHERE book_id = 4 AND library_member_id = 1);
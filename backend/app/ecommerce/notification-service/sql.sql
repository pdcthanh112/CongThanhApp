SELECT 
  r.product AS product_id,
  COUNT(r.id) AS total_reviews,
  COUNT(rm.id) AS reviews_with_media,
  SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END) AS review_with_rating_5,
  SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END) AS review_with_rating_4,
  SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END) AS review_with_rating_3,
  SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END) AS review_with_rating_2,
  SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END) AS review_with_rating_1,
  AVG(r.rating) AS average_rating
FROM review r
LEFT JOIN review_media rm ON r.id = rm.review
WHERE r.product = '84c8cd3c-2dd8-4754-9b4a-7dbf75f2944d'
GROUP BY r.product;


SELECT * 
FROM review LEFT JOIN review_media ON review.id = review_media.review 
WHERE review.product = '84c8cd3c-2dd8-4754-9b4a-7dbf75f2944d'
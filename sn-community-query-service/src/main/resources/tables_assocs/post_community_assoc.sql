ALTER TABLE post_community_assoc ADD FOREIGN KEY (community_id) REFERENCES community(id);
ALTER TABLE post_community_assoc ADD FOREIGN KEY (post_id) REFERENCES post(id);

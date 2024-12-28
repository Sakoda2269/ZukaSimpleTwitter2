package com.example.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, String>{

}

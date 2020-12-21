package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BbsRepository extends JpaRepository<BulletinBoard, Long>{

  public BulletinBoard findById(int id);
	public void deleteById(int id);
}

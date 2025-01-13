package com.lsi.transaction.repository;

import com.lsi.transaction.entity.Transfer;
import com.lsi.transaction.entity.WithDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithDrawRepository extends JpaRepository<WithDraw, Long> {
}

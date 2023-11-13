package com.gopang.repository;

import com.gopang.entity.CustumUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustumUserDetails, Long > {

    public CustumUserDetails findByEmail(final String email);
}
package com.hshim.lottomanager.database.notice.repository

import com.hshim.lottomanager.database.notice.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NoticeRepository : JpaRepository<Notice, String> {
    @Query(
        """
            select n from Notice n 
            left join fetch n.noticeComments 
        """
    )
    fun findAllPage(pageable: Pageable): Page<Notice>
    @Query(
        """
            select n from Notice n 
            left join fetch n.noticeComments
            where n.title like concat('%', :search, '%') 
            or n.content like concat('%', :search, '%') 
        """
    )
    fun findAllBySearch(pageable: Pageable, search: String): Page<Notice>
}
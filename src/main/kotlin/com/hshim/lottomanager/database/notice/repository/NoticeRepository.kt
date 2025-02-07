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
            order by n.createDate desc
        """
    )
    fun findAllPage(pageable: Pageable): Page<Notice>
    @Query(
        """
            select n from Notice n 
            left join fetch n.noticeComments
            where n.title like concat('%', :search, '%') 
            or n.content like concat('%', :search, '%') 
            order by n.createDate desc
        """
    )
    fun findAllBySearch(pageable: Pageable, search: String): Page<Notice>
}
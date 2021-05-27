package backend.repository;

import backend.model.notification;
import backend.model.salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<notification, Long> {

    @Query(
            value = "select * from notification where cin_personne=?1 order by id desc limit 1 ",
            nativeQuery = true)
    notification getNotification(long id);

    @Query(
            value = "select * from notification where cin_personne=?1 order by id desc ",
            nativeQuery = true)
     List<notification> findAllNotif(long id);

    @Query(
            value = "select count(viewed) from notification where cin_personne=?1 and viewed=false ",
            nativeQuery = true)
    int nbNotif(long id);


    @Modifying
    @Query("update notification set viewed=true where cin_personne=:id")
    void update(@Param("id") long id);

}

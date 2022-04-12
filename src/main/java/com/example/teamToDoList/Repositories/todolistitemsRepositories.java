package com.example.teamToDoList.Repositories;


import org.springframework.stereotype.Repository;


@Repository
public interface todolistitemsRepositories extends JpaRepository<todolistitems,Long> {

}

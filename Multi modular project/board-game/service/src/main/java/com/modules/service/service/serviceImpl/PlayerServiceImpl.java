package com.modules.service.service.serviceImpl;

import com.modules.service.entity.Player;
import com.modules.service.repository.PlayerRepository;
import com.modules.service.service.PlayerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    @PostConstruct
    public void initPlayers(){
            repository.saveAll(Stream.of(new Player(1L,"Luffy","King",1,0),
                    new Player(2L,"Zoro","Lost",1,0)).collect(Collectors.toList()));
    }

    @Override
    public List<Player> getAllPlayer() {
        return repository.findAll();
    }
}

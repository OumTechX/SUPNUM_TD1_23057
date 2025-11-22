package supnum.td1.servermanagement;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerRepository repo;

    public ServerController(ServerRepository repo) {
        this.repo = repo;
    }

    // 1 – Lister tous les serveurs
    @GetMapping
    public List<Server> getAll() {
        return repo.findAll();
    }

    // 2 – Créer un serveur
    @PostMapping
    public Server create(@RequestBody Server s) {
        return repo.save(s);
    }

    // 3 – Renommer un serveur
    @PutMapping("/{id}/rename")
    public Server rename(@PathVariable Long id, @RequestBody Server s) {
        Server server = repo.findById(id).orElseThrow();
        server.setName(s.getName());
        return repo.save(server);
    }

    // 4 – Obtenir le statut
    @GetMapping("/{id}/status")
    public boolean getStatus(@PathVariable Long id) {
        return repo.findById(id).orElseThrow().isStatus();
    }

    // 5 – Démarrer un serveur
    @PutMapping("/{id}/start")
    public Server start(@PathVariable Long id) {
        Server server = repo.findById(id).orElseThrow();
        server.setStatus(true);
        return repo.save(server);
    }

    // 6 – Arrêter un serveur
    @PutMapping("/{id}/stop")
    public Server stop(@PathVariable Long id) {
        Server server = repo.findById(id).orElseThrow();
        server.setStatus(false);
        return repo.save(server);
    }

    // 7 – Supprimer un serveur si stoppé
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Server server = repo.findById(id).orElseThrow();

        if (server.isStatus()) {
            return "Impossible de supprimer un serveur en cours d’exécution !";
        }

        repo.delete(server);
        return "Serveur supprimé.";
    }
}

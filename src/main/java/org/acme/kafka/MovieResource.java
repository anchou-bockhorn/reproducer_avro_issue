package org.acme.kafka;

import ch.abraxas.steuern.polaris.personenregister.api.event.Geschlecht;
import ch.abraxas.steuern.polaris.personenregister.api.event.NatuerlichePerson;
import ch.abraxas.steuern.polaris.personenregister.api.event.PersonUpdated;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/movies")
public class MovieResource {

  @Channel("movies")
  Emitter<PersonUpdated> emitter;

  @GET
  public Response enqueueMovie() {
    String uuid = java.util.UUID.randomUUID().toString();
//    UUID u = new UUID(uuid.getBytes(StandardCharsets.UTF_8));
    emitter.send(
        KafkaRecord.of(
            uuid,
            PersonUpdated.newBuilder()
//                .setGeschlecht(Geschlecht.MAENNLICH)
                .setPerson(
                    NatuerlichePerson.newBuilder()
                        .setGeschlecht(Geschlecht.MAENNLICH)
                        .build()
                ).setAwaitingPartner(true)
                .build()
        )
    );
    return Response.accepted().build();
  }

}

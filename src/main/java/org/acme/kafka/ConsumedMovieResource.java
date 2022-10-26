package org.acme.kafka;

import ch.abraxas.steuern.polaris.personenregister.api.event.PersonUpdated;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
@Path("/consumed-movies")
public class ConsumedMovieResource {

    @Channel("movies-from-kafka")
    Multi<PersonUpdated> movies;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    public Multi<String> stream() {
        return movies.map(movie -> movie.toString());
    }
}

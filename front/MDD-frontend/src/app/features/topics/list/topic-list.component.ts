import { Component } from '@angular/core';
import { TopicService } from './services/topic.service';
import { SessionService } from '../../../services/session.service';

@Component({
  selector: 'app-list',
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.scss'
})
export class TopicListComponent {
    public topics$ = this.topicService.getTopics();
    public subscribedTopics$ = this.sessionService.user?.interestedTopics || [];

    constructor(private topicService: TopicService, private sessionService: SessionService) {}

    public subscribeToTopic(topic: string): void {
        this.topicService.subscribeToTopic(topic).subscribe((user) => {
            this.sessionService.logIn(user);
            this.subscribedTopics$ = user.interestedTopics;
        });
    }

    public unsubscribeToTopic(topic: string): void {
        this.topicService.unsubscribeToTopic(topic).subscribe((user) => {
            this.sessionService.logIn(user);
            this.subscribedTopics$ = user.interestedTopics;
        });
    }
}

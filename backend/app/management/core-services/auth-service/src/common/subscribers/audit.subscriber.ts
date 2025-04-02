import {
  Connection,
  EntitySubscriberInterface,
  EventSubscriber,
  InsertEvent,
  UpdateEvent,
} from 'typeorm';
import { AbstractAuditEntity } from 'src/modules/auth/entities/abstract-audit.entity';
import { REQUEST_USER } from '../constants';

@EventSubscriber()
export class AuditSubscriber
  implements EntitySubscriberInterface<AbstractAuditEntity>
{
  constructor(connection: Connection) {
    connection.subscribers.push(this);
  }

  listenTo() {
    return AbstractAuditEntity;
  }

  beforeInsert(event: InsertEvent<AbstractAuditEntity>) {
    const user = this.getCurrentUser();
    if (user) {
      event.entity.createdBy = user.id;
      event.entity.updatedBy = user.id;
    }
  }

  beforeUpdate(event: UpdateEvent<AbstractAuditEntity>) {
    const user = this.getCurrentUser();
    if (user) {
      event.entity.updatedBy = user.id;
    }
  }

  private getCurrentUser() {
    // Lấy user từ request context
    return global[REQUEST_USER];
  }
}

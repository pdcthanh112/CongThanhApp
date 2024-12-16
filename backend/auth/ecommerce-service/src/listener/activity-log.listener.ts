import { MYSQL_DB } from "@/databases/mysql";
import { ActivityLog } from "@/interfaces/activityLog.interface";


export async function saveActivityLog(activityLog: ActivityLog) {
  return await MYSQL_DB.ActivityLog.create(activityLog);
}

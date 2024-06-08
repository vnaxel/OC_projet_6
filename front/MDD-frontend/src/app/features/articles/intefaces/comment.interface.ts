import { User } from "../../../interfaces/user.interface";

export interface Comment {
    content: string;
    author: User;
}
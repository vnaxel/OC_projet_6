
import { User } from "../../../interfaces/user.interface";
import { Comment } from "./comment.interface";

export interface Article {
    id: number;
    title: string;
    content: string;
    topic: string;
    author: User;
    created_at: string;
    updated_at: string;
    comments: Comment[];
}
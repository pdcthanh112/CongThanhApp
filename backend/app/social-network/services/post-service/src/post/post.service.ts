import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Post } from './entity/post.entity';
import { CreatePostDto, UpdatePostDto } from './request';

@Injectable()
export class PostService {
  constructor(
    @InjectRepository(Post)
    private postRepository: Repository<Post>,
  ) {}

  async create(createPostDto: CreatePostDto, userId: string): Promise<Post> {
    const post = this.postRepository.create({
      ...createPostDto,
      userId,
    });
    return await this.postRepository.save(post);
  }

  async findAll(page: number = 1, limit: number = 10): Promise<{ items: Post[]; total: number }> {
    const [items, total] = await this.postRepository.findAndCount({
      where: { isPublished: true },
      skip: (page - 1) * limit,
      take: limit,
      order: { createdAt: 'DESC' },
    });

    return { items, total };
  }

  async findOne(id: string): Promise<Post> {
    const post = await this.postRepository.findOne({ where: { id } });
    if (!post) {
      throw new NotFoundException(`Post with ID "${id}" not found`);
    }
    return post;
  }

  async update(id: string, updatePostDto: UpdatePostDto, userId: string): Promise<Post> {
    const post = await this.findOne(id);
    if (post.userId !== userId) {
      throw new Error('Unauthorized to update this post');
    }
    await this.postRepository.update(id, updatePostDto);
    return await this.findOne(id);
  }

  async remove(id: string, userId: string): Promise<void> {
    const post = await this.findOne(id);
    if (post.userId !== userId) {
      throw new Error('Unauthorized to delete this post');
    }
    await this.postRepository.delete(id);
  }

  async likePost(id: string): Promise<Post> {
    const post = await this.findOne(id);
    post.likes += 1;
    return await this.postRepository.save(post);
  }

  async findByUserId(userId: string): Promise<Post[]> {
    return await this.postRepository.find({
      where: { userId },
      order: { createdAt: 'DESC' },
    });
  }
}

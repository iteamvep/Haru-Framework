/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.cache.ehcache;

/**
 *
 * @author iTeamVEP
 */
public class EhCacheConfigs {
        /**
	 * 堆缓存大小 单位KB
	 */
	private int HEAP_CACHE_SIZE = 10;
	
	/**
	 * 堆外缓存大小 单位MB
	 */
	private int OFF_HEAP_CACHE_SIZE = 20;
	
	/**
	 * 磁盘缓存大小 单位MB
	 */
	private int DISK_CACHE_SIZE = 100;
	
	/**
	 * 堆可缓存的最大对象大小 单位MB
	 */
	private long HEAP_MAX_OBJECT_SIZE = 1L;
	
	/**
	 * 统计对象大小时对象图遍历深度
	 */
	private long HEAP_MAX_OBJECT_GRAPH = 1000L;
	
	/**
	 * 磁盘文件路径
	 */
	private String DISK_CACHE_DIR = "/tmp/cache";
	
	/**
	 *ehcache缓存超时时间 单位秒
	 */
	private int EHCACHE_TTL = 120;
	
	
    /**
     * @return the HEAP_CACHE_SIZE
     */
    public int getHEAP_CACHE_SIZE() {
        return HEAP_CACHE_SIZE;
    }

    /**
     * @param HEAP_CACHE_SIZE the HEAP_CACHE_SIZE to set
     */
    public void setHEAP_CACHE_SIZE(int HEAP_CACHE_SIZE) {
        this.HEAP_CACHE_SIZE = HEAP_CACHE_SIZE;
    }

    /**
     * @return the OFF_HEAP_CACHE_SIZE
     */
    public int getOFF_HEAP_CACHE_SIZE() {
        return OFF_HEAP_CACHE_SIZE;
    }

    /**
     * @param OFF_HEAP_CACHE_SIZE the OFF_HEAP_CACHE_SIZE to set
     */
    public void setOFF_HEAP_CACHE_SIZE(int OFF_HEAP_CACHE_SIZE) {
        this.OFF_HEAP_CACHE_SIZE = OFF_HEAP_CACHE_SIZE;
    }

    /**
     * @return the DISK_CACHE_SIZE
     */
    public int getDISK_CACHE_SIZE() {
        return DISK_CACHE_SIZE;
    }

    /**
     * @param DISK_CACHE_SIZE the DISK_CACHE_SIZE to set
     */
    public void setDISK_CACHE_SIZE(int DISK_CACHE_SIZE) {
        this.DISK_CACHE_SIZE = DISK_CACHE_SIZE;
    }

    /**
     * @return the HEAP_MAX_OBJECT_SIZE
     */
    public long getHEAP_MAX_OBJECT_SIZE() {
        return HEAP_MAX_OBJECT_SIZE;
    }

    /**
     * @param HEAP_MAX_OBJECT_SIZE the HEAP_MAX_OBJECT_SIZE to set
     */
    public void setHEAP_MAX_OBJECT_SIZE(long HEAP_MAX_OBJECT_SIZE) {
        this.HEAP_MAX_OBJECT_SIZE = HEAP_MAX_OBJECT_SIZE;
    }

    /**
     * @return the HEAP_MAX_OBJECT_GRAPH
     */
    public long getHEAP_MAX_OBJECT_GRAPH() {
        return HEAP_MAX_OBJECT_GRAPH;
    }

    /**
     * @param HEAP_MAX_OBJECT_GRAPH the HEAP_MAX_OBJECT_GRAPH to set
     */
    public void setHEAP_MAX_OBJECT_GRAPH(long HEAP_MAX_OBJECT_GRAPH) {
        this.HEAP_MAX_OBJECT_GRAPH = HEAP_MAX_OBJECT_GRAPH;
    }

    /**
     * @return the DISK_CACHE_DIR
     */
    public String getDISK_CACHE_DIR() {
        return DISK_CACHE_DIR;
    }

    /**
     * @param DISK_CACHE_DIR the DISK_CACHE_DIR to set
     */
    public void setDISK_CACHE_DIR(String DISK_CACHE_DIR) {
        this.DISK_CACHE_DIR = DISK_CACHE_DIR;
    }

    /**
     * @return the EHCACHE_TTL
     */
    public int getEHCACHE_TTL() {
        return EHCACHE_TTL;
    }

    /**
     * @param EHCACHE_TTL the EHCACHE_TTL to set
     */
    public void setEHCACHE_TTL(int EHCACHE_TTL) {
        this.EHCACHE_TTL = EHCACHE_TTL;
    }
	
}
